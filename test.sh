rm -rf sample &&
  sbt publish-local &&
  cp -r src/test/resources/sample_project sample &&
  cd sample &&
  sbt compile-sxt
  