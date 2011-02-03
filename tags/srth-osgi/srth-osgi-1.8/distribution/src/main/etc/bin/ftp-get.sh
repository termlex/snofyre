#!/bin/sh
####### FTP Script for downloading TRUD archive #########
cd %{INSTALL_PATH}
ftp -n www.uktcdownload.nss.cfh.nhs.uk << EOF
user %{trud.user.name} %{trud.password}
prompt off
cd NHSSNOFYRE
cd 2.0.0
cd NHS_SNOFYREDATA
bin
get ${trud.pack.name}
disconnect
bye
EOF
