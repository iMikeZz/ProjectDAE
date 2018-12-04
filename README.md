# ProjectDAE

<b> REPOSITORY: </b> </br>
Project of Desenvolvimento de Aplicações Empresariais

<b> SETUP: </b>


<ol>
<i><b>Setup of DB:</b></i>
  <li><b>Database Name:</b> DAE_PROJECT</li>
  <li><b>User Name:</b> dae</li>
  <li><b>Password:</b> dae</li>
  <li><b>JNDI in file persistence.xml:</b>dae_project_management</li>
</ol>

<ol>
<i><b>Setup of jdbcRealm:</b></i>
    <li>Configurations -> server-config -> Security -> Realms -> New</li>
    <li>Realm name: project_dae_realm </li>
    <li>Class name: dropdown and choose "com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm"</li>
    <li>JAAS Context: jdbcRealm</li>
    <li>JNDI: project_dae</li>
    <li>User Table: USERS</li>
    <li>User Name Column: USERNAME</li>
    <li>Password Column: PASSWORD</li>
    <li>Group Table: USERS_GROUPS</li>
    <li>Group Table User Name Column: USERNAME</li>
    <li>Group Name Column: GROUPNAME</li>
    <li>Password Encryption Algorithm: SHA-256</li>
    <li>Database User: dae</li>
    <li>Database Password: dae</li>
    <li>All the other fields remain empty.</li>
    <li>Ok</li>
</ol>
