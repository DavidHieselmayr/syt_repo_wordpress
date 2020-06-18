#!/bin/bash
echo Photo-Packager V0.1
echo ===================
echo Wie heißt Du?
echo Griasdi, $USER!
echo `date +%d.%m.%Y`

echo "<html><head><title>Foto-Index</title></head><body><h1>Foto-Index</h1><table>" > index.html
for i in `cut -d: -f1 bilder.txt`
do
  echo "<tr><td>"$i"</td><td><img src ='./bilder/"$i >>index.html
  echo "'</td></tr>" >> index.html
done
echo "</table></body></html" >> index.html

tar cfv photos.tar.gz bilder bilder.txt index.html package-phots.sh 
tar tfv photos.tar.gz
