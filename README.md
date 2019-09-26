# Software needed
1.	[Apache Maven] (http://maven.apache.org)
2.	[Docker] (http://docker.com) 
3.	[Git Client] (http://git-scm.com)

# Building the Docker Images 
To build the code examples as a docker image, open a command-line window change to the directory where you have downloaded source code.

Run the following maven command.  This command will execute the [Spotify docker plugin](https://github.com/spotify/docker-maven-plugin) defined in the pom.xml file.  

   **mvn clean package docker:build**

Running the above command at the root of the project directory will build all of the projects.  If everything builds successfully you should see a message indicating that the build was successful.

# Running the services 

Now we are going to use docker-compose to start the actual image.  To start the docker image,
change to the directory containing your source code.  Issue the following docker-compose command:

   **docker-compose -f docker/common/docker-compose.yml up**

If everything starts correctly you should see a bunch of Spring Boot information fly by on standard out.  At this point all of the services needed for the code examples will be running.
