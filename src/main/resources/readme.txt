# .bash_profile

export DERBY_HOME=/home/palermo/db-derby-10.14.2.0-bin
export PATH="$DERBY_HOME/bin:$PATH"
export DERBY_OPTS="-Dderby.system.home=$DERBY_HOME/data"

# mingw .bash_profile
export DERBY_HOME=/c/java/db-derby-10.14.2.0-bin
export PATH="$DERBY_HOME/bin:$PATH"
export DERBY_OPTS="-Dderby.system.home=$DERBY_HOME/data"


# ij
CONNECT 'jdbc:derby://localhost:1527/article';
