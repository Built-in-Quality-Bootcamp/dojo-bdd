echo $1
echo "./gradlew cucumber -PcucumberOptions='-t, $1'"
./gradlew cucumber -PcucumberOptions="-t, $1"
