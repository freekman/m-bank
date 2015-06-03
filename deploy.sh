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
	then echo 'Failed to jar backend project!'
	return
fi

# Transfer packaged project to dev
devdest=clouway@dev.telcong.com:/opt/telcong/incubator/m-bank/

rsync -v config.properties $devdest
rsync -v target/BrickBank-jar-with-dependencies.jar $devdest
rsync -vr src/main/webapp/ $devdest/frontend/

# Start up newly transferred project
ssh clouway@dev.telcong.com sudo stop upstmbank
ssh clouway@dev.telcong.com sudo start upstmbank
