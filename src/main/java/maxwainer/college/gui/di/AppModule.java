package maxwainer.college.gui.di;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import maxwainer.college.gui.common.MoreResources;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.pages.LoginPageController;
import maxwainer.college.gui.pages.MainPageController;
import maxwainer.college.gui.pages.RegisterPageController;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.jetbrains.annotations.NotNull;

public final class AppModule extends AbstractModule {

  private final Stage parentStage;

  public AppModule(final @NotNull Stage parentStage) {
    this.parentStage = parentStage;
  }

  @Override
  protected void configure() {
    // basic values
    this.bind(FXMLLoader.class).toProvider(new FXMLProvider());
    this.bind(AppValues.class).toInstance(new AppValues());
    this.bind(AppConfig.class).toInstance(new AppConfig(MoreResources.properties("appconfig")));
    this.bind(Stage.class).toInstance(parentStage);

    // pages
    this.bind(LoginPageController.class).toInstance(new LoginPageController());
    this.bind(MainPageController.class).toInstance(new MainPageController());
    this.bind(RegisterPageController.class).toInstance(new RegisterPageController());

    // utils

    // okhttp
    // start
    try {
      final TrustManager[] trustEverything = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[]{};
            }
          }
      };

      final var context = SSLContext.getInstance("SSL");

      context.init(null, trustEverything, new SecureRandom());

      final var socketFactory = context.getSocketFactory();

      this.bind(OkHttpClient.class).toInstance(new OkHttpClient.Builder()
          .retryOnConnectionFailure(true)
          .sslSocketFactory(socketFactory, (X509TrustManager) trustEverything[0])
          .hostnameVerifier((hostname, session) -> true)
          .build());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new RuntimeException(e);
    }
    // end

    // gson
    this.bind(Gson.class).toInstance(new Gson());

    // create web fetcher
    final WebFetcherRegistryImpl webFetcherRegistry = new WebFetcherRegistryImpl();
    webFetcherRegistry.configure(this.binder()); // configure internals for fetchers

    this.bind(WebFetcherRegistry.class).toInstance(webFetcherRegistry);
  }
}
