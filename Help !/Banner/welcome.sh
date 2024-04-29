#!/bin/sh

USER=$(id -u)

if [ $USER -eq "0" ]
then
        exit 0
fi

if [[ $(who | cut -d " " -f1) -eq $(whoami) ]]
then
        LOG_FROM=$(who | cut -d "(" -f2 | cut -d ")" -f1)
fi


NONE="\033[m"
NONE="\033[1;37m"
GREEN="\033[1;32m"
RED="\033[0;32;31m"
YELLOW="\033[1;33m"
BLUE="\033[34m"
CYAN="\033[36m"
LIGHT_GREEN="\033[1;32m"
LIGHT_RED="\033[1;31m"


hostname=`uname -n`
diskSpace=$(df -Ph | grep md3 | awk '{print $4}')
memoryUsed=`free -t -m | grep Total | awk '{print $3" MB";}'`

# echo "
# ===========================================
#  - Hostname............: $hostName
#  - Disk Space..........: $diskSpace
#  - Memory used.........: $memoryUsed
# ===========================================
# "

printf "\n"$LIGHT_RED
figlet -f bloody -w 1000 $(hostname)
printf $NONE
printf "\n"

#proc=`cat /proc/cpuinfo | grep -i "^model name" | awk -F": " '{print $2}'`
memfree=`cat /proc/meminfo | grep MemFree | awk {'print $2'}`
memtotal=`cat /proc/meminfo | grep MemTotal | awk {'print $2'}`
uptime=`uptime -p`
addrip=`hostname -I | cut -d " " -f1`
# Récupérer le loadavg
read one five fifteen rest < /proc/loadavg

# Affichage des variables
#printf "  Processeur : $proc"

printf " $NONE Username   $NONE : $GREEN $(whoami) $NONE \\n"
printf " $NONE Hostname   $NONE : $BLUE $hostname $NONE \\n"
#printf " $NONE CPU        $NONE : $BLUE $one (1min) / $five (5min) / $fifteen (15min) $NONE \\n"
printf " $NONE IP Address $NONE : $GREEN $addrip $NONE \\n"
printf " $NONE Memory Used$NONE : $BLUE $memoryUsed $NONE \\n"
printf " $NONE Disk Space $NONE : $BLUE $diskSpace $NONE \\n"
printf " $NONE Uptime     $NONE : $BLUE $uptime $NONE \\n\\n"
printf " $NONE Log From   $NONE : $GREEN $LOG_FROM $NONE \\n"
printf "\\n\\n"

