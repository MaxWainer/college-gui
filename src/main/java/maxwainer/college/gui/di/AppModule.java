package maxwainer.college.gui.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import marcono1234.gson.recordadapter.RecordTypeAdapterFactory;
import maxwainer.college.gui.common.MoreResources;
import maxwainer.college.gui.common.ThreadFactories;
import maxwainer.college.gui.common.gson.LocalDateTimeSerializer;
import maxwainer.college.gui.config.AppConfig;
import maxwainer.college.gui.pages.MainPageController;
import maxwainer.college.gui.pages.auth.LoginPageController;
import maxwainer.college.gui.pages.auth.RegisterPageController;
import maxwainer.college.gui.task.WebServiceHeartbeatListener;
import maxwainer.college.gui.values.AppValues;
import maxwainer.college.gui.web.WebFetcherRegistry;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public final class AppModule extends AbstractModule {

  private final Stage parentStage;

  public AppModule(final @NotNull Stage parentStage) {
    this.parentStage = parentStage;
  }

  @Override
  protected void configure() {
    final var config = new AppConfig(MoreResources.properties("appConfig"));

    // basic values
    this.bind(FXMLLoader.class).toProvider(new FXMLProvider());
    this.bind(AppValues.class).toInstance(new AppValues());
    this.bind(AppConfig.class).toInstance(config);
    this.bind(Stage.class).toInstance(parentStage);

    // pages
    this.bind(LoginPageController.class).toInstance(new LoginPageController());
    this.bind(MainPageController.class).toInstance(new MainPageController());
    this.bind(RegisterPageController.class).toInstance(new RegisterPageController());

    // utils

    // okhttp
    // start
    try {
      final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
          .retryOnConnectionFailure(true);

      if (!config.checkConnectionCertificate()) {
        disableCertification(clientBuilder);
      }

      this.bind(OkHttpClient.class).toInstance(clientBuilder.build());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new RuntimeException(e);
    }
    // end

    // gson
    this.bind(Gson.class).toInstance(
        new GsonBuilder()
            // See: https://github.com/google/gson/issues/1794
            .registerTypeAdapterFactory(RecordTypeAdapterFactory.builder()
                .allowMissingComponentValues() // we can have missing components
                .allowJsonNullForPrimitiveComponents()
                .create()
            )
            // Need to C# format backward support
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .setLenient()
            .serializeNulls()
            .create());

    // create web fetcher
    final WebFetcherRegistryImpl webFetcherRegistry = new WebFetcherRegistryImpl();
    webFetcherRegistry.configure(this.binder()); // configure internals for fetchers

    this.bind(WebFetcherRegistry.class).toInstance(webFetcherRegistry);

    // scheduler services
    bind(ScheduledExecutorService.class)
        .toInstance(
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                ThreadFactories.createFactory("internal-application-service-%s")));

    bind(WebServiceHeartbeatListener.class).toInstance(new WebServiceHeartbeatListener());
  }

  private static void disableCertification(final @NotNull OkHttpClient.Builder builder)
      throws KeyManagementException, NoSuchAlgorithmException {
    // disable certificate checks
    final var trustEverything = new TrustManager[]{
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

    // init context
    context.init(null, trustEverything, new SecureRandom());

    final var socketFactory = context.getSocketFactory();

    builder
        .sslSocketFactory(socketFactory, (X509TrustManager) trustEverything[0])
        // trust everything
        .hostnameVerifier((hostname, session) -> true);
  }
}
