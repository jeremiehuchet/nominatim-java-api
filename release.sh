#!/bin/bash -e

# Uploads artifacts produced by the build of the current tag to staging repository.

basedir=$(cd $(dirname $0) ; pwd)

if [[ $(git tag -l --points-at HEAD | wc -l) != 1 ]] ; then
  echo HEAD must point to a single git tag
  echo '  (use "git tag <tag name>" to create a tag)'
  exit 1
fi

VERSION=$(git tag -l --points-at HEAD | sed 's/^v//g')
echo Releasing ${VERSION}

if [[ -n "$(git status --porcelain)" ]] ; then
  echo Workspace must be clean
  echo '  (use "git status" to see uncommited changes)'
  exit 1
fi

echo -n "Sonatype OSS Nexus username: "
read NEXUS_USERNAME

echo -n "Sonatype OSS Nexus password: "
read -s NEXUS_PASSWORD

function release() {
  mvn gpg:sign-and-deploy-file \
    -s ${basedir}/target/release-settings.xml \
    -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ \
    -DrepositoryId=ossrh \
    -DpomFile=${basedir}/target/nominatim-api-flattened.pom \
    -Dfile=${basedir}/target/$1 \
    -Dclassifier=$2
}

mvn clean package -Drevision=${VERSION}

# publish javadoc
git clone git@github.com:jeremiehuchet/nominatim-java-api.git ${basedir}/target/release-gh-pages && (
  cd ${basedir}/target/release-gh-pages
  git checkout gh-pages
  mkdir ${basedir}/target/release-gh-pages/${VERSION}
  cd ${basedir}/target/release-gh-pages/${VERSION}
  jar xf ${basedir}/target/nominatim-api-javadoc.jar
  cd ..
  rm -f latest
  ln -s ${VERSION} latest
  find -mindepth 1 -maxdepth 1 ! -type f ! -name '.*' -printf '%P\n' | sort > index.txt
  git add .
  git commit -m "update javadoc with ${VERSION}"
  git push origin gh-pages
)

# publish artifacts
cat - <<EOF > ${basedir}/target/release-settings.xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${NEXUS_USERNAME}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
  </servers>
</settings>
EOF

cp .flattened-pom.xml target/nominatim-api-flattened.pom
release nominatim-api.jar
release nominatim-api-sources.jar sources
release nominatim-api-javadoc.jar javadoc