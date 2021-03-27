package com.example.cloudwrite.bootstrap;

import com.example.cloudwrite.JPARepository.AuthorityRepo;
import com.example.cloudwrite.JPARepository.UserRepo;
import com.example.cloudwrite.model.*;
import com.example.cloudwrite.model.security.Authority;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile(value = {"SQL", "SDjpa"})
public class DataLoader implements CommandLineRunner {

    private final FundamentalPieceService fundamentalPieceService;
    private final ExpositionPieceService expositionPieceService;
    private final ConceptService conceptService;
    private final KeyResultService keyResultService;
    private final StandfirstService standfirstService;

    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder DBpasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        if (fundamentalPieceService.findAll() == null || fundamentalPieceService.findAll().size() == 0){
            buildFundamentalPieces();
        } else {
            log.info("Fundamental pieces already on file; nothing loaded");
        }

        if (expositionPieceService.findAll() == null || expositionPieceService.findAll().size() == 0){
            buildResearchExpositions();
        } else {
            log.info("Exposition pieces already on file; nothing loaded");
        }

        if (authorityRepo.findAll().size() == 0){
            addUsers();
            log.debug("User databases finished populating");
        } else
            log.debug("User databases already contains data; no changes made");

        log.info("All data loaded");
    }

    private void buildFundamentalPieces(){

        Concept bioConcept = Concept.builder()
                .description("Some random concept in Biology").purpose("Explain the origin of life").build();
        Concept chemConcept = Concept.builder()
                .description("Some random concept in Chemistry").purpose("Why are electrons needed").build();

        Concept savedBio = conceptService.save(bioConcept);
        Concept savedChem = conceptService.save(chemConcept);

        FundamentalPiece bioPiece = FundamentalPiece.builder()
                .conceptList(List.of(savedBio))
                .keyword("keyword1")
                .prerequisites("The necessary knowhow")
                .title("Biology 101")
                .summary("Biological summary")
                .build();

        FundamentalPiece chemPiece = FundamentalPiece.builder()
                .conceptList(List.of(savedChem))
                .keyword("keyword2")
                .prerequisites("The necessary knowhow again")
                .title("Chemistry 101")
                .summary("Chemical summary")
                .build();

        FundamentalPiece savedChemPiece = fundamentalPieceService.save(chemPiece);
        FundamentalPiece savedBioPiece = fundamentalPieceService.save(bioPiece);

        savedBio.setFundamentalPiece(savedBioPiece);
        savedChem.setFundamentalPiece(savedChemPiece);

        conceptService.save(savedBio);
        conceptService.save(savedChem);

        log.info("Saved " + fundamentalPieceService.findAll().size() + " piece(s)");
    }

    private void buildResearchExpositions(){

        KeyResult someResult = KeyResult.builder().description("Some description").build();
        Standfirst standfirst = Standfirst.builder().rationale("What's wrong?").approach("The approach").build();

        Standfirst savedStandfirst = standfirstService.save(standfirst);
        KeyResult savedResult = keyResultService.save(someResult);

        ExpositionPiece piece = ExpositionPiece.builder()
                .title("The best piece in the world!")
                .expositionPurpose("A purpose")
                .currentProgress("The current state")
                .keyword("Keyword")
                .keyResults(List.of(savedResult))
                .standfirst(savedStandfirst)
                .futureWork("What to expect").build();

        ExpositionPiece savedPiece = expositionPieceService.save(piece);

        savedResult.setExpositionPiece(savedPiece);
        savedStandfirst.setExpositionPiece(savedPiece);

        standfirstService.save(savedStandfirst);
        keyResultService.save(savedResult);

        log.info("Saved " + expositionPieceService.findAll().size() + " piece(s)");
    }

    private void addUsers(){
        //example, as per Student Record Management (SRM) account
        Authority adminAuthority = authorityRepo.save(Authority.builder().role("ADMIN").build());
        Authority teacherAuthority = authorityRepo.save(Authority.builder().role("USER").build());
        log.debug("Authorities added: " + authorityRepo.findAll().size());

        userRepo.save(User.builder()
                .username("admin")
                .password(DBpasswordEncoder.encode("admin123"))
                .authority(adminAuthority)  //singular set, courtesy of Project Lombok
                .build());
        userRepo.save(User.builder()
                .username("user")
                .password(DBpasswordEncoder.encode("user123"))
                .authority(teacherAuthority)
                .build());

        log.debug("Users added: " + userRepo.findAll().size());
    }
}
