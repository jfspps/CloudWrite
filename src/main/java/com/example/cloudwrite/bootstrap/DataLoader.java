package com.example.cloudwrite.bootstrap;

import com.example.cloudwrite.JPARepository.AuthorityRepo;
import com.example.cloudwrite.JPARepository.UserRepo;
import com.example.cloudwrite.model.*;
import com.example.cloudwrite.model.security.Authority;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.*;
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
@Profile(value = {"SDjpa"})
public class DataLoader implements CommandLineRunner {

    private final FundamentalPieceService fundamentalPieceService;
    private final ResearchPieceService researchPieceService;
    private final ConceptService conceptService;
    private final KeyResultService keyResultService;
    private final StandfirstService standfirstService;
    private final CitationService citationService;

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

        if (researchPieceService.findAll() == null || researchPieceService.findAll().size() == 0){
            buildResearchResearch();
        } else {
            log.info("Research pieces already on file; nothing loaded");
        }

        // reset user login credentials on each run
        addUsers();

        log.info("All data loaded");
    }

    private void buildFundamentalPieces(){

        Concept bioConcept = Concept.builder()
                .description("Some random concept in Biology").purpose("Explain the origin of life").build();
        Concept chemConcept = Concept.builder()
                .description("Some random concept in Chemistry").purpose("Why are electrons needed").build();
        Concept chemConcept2 = Concept.builder()
                .description("Another random concept in Chemistry").purpose("Why are protons needed").build();

        Concept savedBio = conceptService.save(bioConcept);
        Concept savedChem = conceptService.save(chemConcept);
        Concept savedChem2 = conceptService.save(chemConcept2);

        FundamentalPiece bioPiece = FundamentalPiece.builder()
                .conceptList(List.of(savedBio))
                .keyword("keyword1")
                .prerequisites("The necessary knowhow")
                .title("Biology 101")
                .summary("Biological summary")
                .build();

        FundamentalPiece chemPiece = FundamentalPiece.builder()
                .conceptList(List.of(savedChem, savedChem2))
                .keyword("keyword2")
                .prerequisites("The necessary knowhow again")
                .title("Chemistry 101")
                .summary("Chemical summary")
                .build();

        FundamentalPiece savedChemPiece = fundamentalPieceService.save(chemPiece);
        FundamentalPiece savedBioPiece = fundamentalPieceService.save(bioPiece);

        savedBio.setFundamentalPiece(savedBioPiece);
        savedChem.setFundamentalPiece(savedChemPiece);
        savedChem2.setFundamentalPiece(savedChemPiece);

        conceptService.save(savedBio);
        conceptService.save(savedChem);
        conceptService.save(savedChem2);

        log.info("Saved " + fundamentalPieceService.findAll().size() + " piece(s)");
    }

    private void buildResearchResearch(){

        KeyResult someResult = KeyResult.builder().description("Some first description").priority(1).build();
        KeyResult someResult2 = KeyResult.builder().description("Some second description").priority(2).build();

        Standfirst standfirst = Standfirst.builder().rationale("What's wrong?").approach("The approach").build();
        Citation citation = Citation.builder().reference("The Journal of this and that").build();
        Standfirst savedStandfirst = standfirstService.save(standfirst);

        KeyResult savedResult = keyResultService.save(someResult);
        KeyResult savedResult2 = keyResultService.save(someResult2);

        Citation savedCitation = citationService.save(citation);

        ResearchPiece piece = ResearchPiece.builder()
                .title("The best piece in the world!")
                .researchPurpose("A purpose")
                .currentProgress("The current state")
                .keyword("Keyword")
                .keyResults(List.of(savedResult, savedResult2))
                .standfirst(savedStandfirst)
                .citations(List.of(savedCitation))
                .futureWork("What to expect").build();

        ResearchPiece savedPiece = researchPieceService.save(piece);

        savedResult.setResearchPiece(savedPiece);
        savedResult2.setResearchPiece(savedPiece);
        savedStandfirst.setResearchPiece(savedPiece);
        savedCitation.setPiece(savedPiece);

        standfirstService.save(savedStandfirst);
        keyResultService.save(savedResult);
        keyResultService.save(savedResult2);
        citationService.save(savedCitation);

        log.info("Saved " + researchPieceService.findAll().size() + " piece(s)");
    }

    private void addUsers(){
        if (!userRepo.findAll().isEmpty()){
            userRepo.findAll().forEach(userRepo::delete);
        }

        //use ROLE_ prefix with JPAUserDetailsService
        Authority adminAuthority = authorityRepo.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority teacherAuthority = authorityRepo.save(Authority.builder().role("ROLE_USER").build());
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
