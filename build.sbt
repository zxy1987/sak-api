name := """sak-api"""
organization := "com.sak"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(guice, javaJdbc, javaWs)