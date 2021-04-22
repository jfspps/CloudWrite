# Instructions

With Dockerfile and the JAR (typically found in /target) in the same directory, build the CentOS based image with

```bash
docker build -t cloud-write-docker .
```

Don't miss out the period above! Then run the container (--name is optional) from the console (add -d for daemon) with

```bash
docker run -p 5000:5000 --name writer cloud-write-docker
```

The host port (you) is the first port number. CloudWrite is set to publish from port 5000 (the second port number).
