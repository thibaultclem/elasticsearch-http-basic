
package com.asquera.elasticsearch.plugins.http.auth.integration;


import com.asquera.elasticsearch.plugins.http.HttpBasicServerPlugin;
import org.apache.http.impl.client.HttpClients;
import org.elasticsearch.common.Base64;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.elasticsearch.test.rest.client.http.HttpRequestBuilder;

/**
 *
 * @author Ernesto Miguez (ernesto.miguez@asquera.de)
 */

public class HttpBasicServerPluginIntegrationTest extends
ElasticsearchIntegrationTest {

  protected final String localhost = "127.0.0.1";


  /**
   *
   * @return a Builder with the plugin included and bind_host and publish_host
   * set to localhost, from where the client's request ip will be done.
   */
  protected Builder builderWithPlugin() {
    return ImmutableSettings.settingsBuilder()
      .put("network.host", localhost)
      .put("plugin.types", HttpBasicServerPlugin.class.getName());
  }

  protected HttpRequestBuilder requestWithCredentials(String credentials) throws Exception {
        return httpTestClient().path("/_status")
          .addHeader("Authorization", "Basic " + Base64.encodeBytes(credentials.getBytes()));
  }

  public static HttpRequestBuilder httpTestClient() {
    return new HttpRequestBuilder(HttpClients.createDefault())
        .host(cluster().httpAddresses()[0].getHostName())
        .port(cluster().httpAddresses()[0].getPort()
        );
  }

}
