
# Lab 6

## This is a hash table, using the Kotlin language

<br>

### How to run & build

    ./gradlew run

## Add Kotlin class to Java Maven Project

<br>

### - Add this lab to your local repo

    ./gradlew  publishMavenPublicationToMavenLocal

### - In pom.xml of your Java Maven Project add this sections(or merge)

     <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>1.8.21</version>
        </dependency>
     </dependencies>


     <build>
        <plugins>
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <version>1.8.21</version>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>process-sources</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>test-compile</id>
                        <goals>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <jvmTarget>1.8</jvmTarget>
                </configuration>
            </plugin>
        </plugins>
    </build>
