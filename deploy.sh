#!bin/bash

# Move to frontend project and prepare resources
cd ngbp
grunt packageBank
if [ $? -ne 0 ]
	then echo 'Transfering forntend resources failed!'
	return
fi

# Moove to backend project and jar source
cd ../bank
mvn clean package
if [ $? -ne 0 ]
	then echo 'Failed to jar backend project.!'
	return
fi

# Transfer packaged project to dev
destination=/home/clouway/demo-dev/

devdest=clouway@dev.telcong.com:/opt/telcong/incubator/m-bank/

rsync -v config.properties $destination
rsync -v target/BrickBank-jar-with-dependencies.jar $destination
rsync -vr src/main/webapp/ $destination/frontend/

# Start up newly transferred project
cd $destination
java -jar BrickBank-jar-with-dependencies.jar config.properties 
cd
