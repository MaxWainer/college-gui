module maxwainer.college.gui {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  opens maxwainer.college.gui.object.web to
      marcono1234.gson.recordadapter,
      com.google.gson;

  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires org.jetbrains.annotations;
  requires com.google.common;
  requires okhttp3;
  requires com.google.guice;
  requires com.google.gson;

  requires java.logging;
  requires java.base;
  requires java.compiler;
  requires com.dlsc.formsfx;
  requires marcono1234.gson.recordadapter;
}