package maxwainer.college.gui.web.implementation;

import okhttp3.MediaType;

public interface MediaTypes {

  MediaType JSON = MediaType.parse("application/json");

  MediaType TEXT = MediaType.parse("text/plain");

  MediaType TEXT_UTF8 = MediaType.parse("text/plain;charset=UTF-8");

}
