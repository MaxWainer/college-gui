module maxwainer.college.gui.collegegui {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires org.jetbrains.annotations;
  requires com.google.common;
  requires guice;
  requires okhttp3;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  opens maxwainer.college.gui to javafx.fxml;
  exports maxwainer.college.gui;
}