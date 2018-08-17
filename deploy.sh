if [ ! -d "/root/cms" ]; then
echo '/root/cms not exist! creating'
mkdir /root/cms
echo 'create dir /root/cms succeed'
fi
\cp -f admin/build/libs/admin-*.jar /root/cms/
echo 'copy' `ls admin/build/libs/admin-*.jar | tr '\n' '\0' | xargs -0 -n 1 basename` 'to /root/cms/!'
if [ -s "/var/run/cms-admin.pid" ]; then
kill `cat /var/run/cms-admin.pid`
echo 'kill pid: ' `cat /var/run/cms-admin.pid` 'succeed'
fi
nohup java -jar admin/build/libs/admin-*.jar >> /var/log/cms-admin.log 2>&1 &
echo $! > /var/run/cms-admin.pid
echo 'deploy succeed! pid:' `cat /var/run/cms-admin.pid`
