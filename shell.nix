{ pkgs ? import <nixpkgs> { } }:

pkgs.mkShell {
  name = "nominatim-java-api";
  buildInputs = with pkgs; [
    jdk8_headless
    (maven.override {
      jdk = jdk8_headless;
    })
  ];
}
