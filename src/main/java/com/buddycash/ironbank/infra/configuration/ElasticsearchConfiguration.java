package com.buddycash.ironbank.infra.configuration;

import co.elastic.clients.transport.TransportUtils;
import java.io.FileInputStream;
import java.io.IOException;
import javax.net.ssl.SSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.StringUtils;

@EnableElasticsearchRepositories
@Configuration
public class ElasticsearchConfiguration extends
    org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration {

  @Value("${spring.elasticsearch.uris}")
  private String springElasticsearchUris;

  @Value("${spring.elasticsearch.username}")
  private String springElasticsearchUsername;

  @Value("${spring.elasticsearch.password}")
  private String springElasticsearchPassword;
  @Override
  public ClientConfiguration clientConfiguration() {
    var host = springElasticsearchUris.replace("https://", "").replace("http://", "");
    var builder = ClientConfiguration.builder()
        .connectedTo(host);
    if (springElasticsearchUris.contains("https://")) {
      var sslContext = getSSLContext();
      builder.usingSsl(sslContext);
    }
    if (springElasticsearchUsername != null && springElasticsearchUsername.length() > 0) {
      builder.withBasicAuth(springElasticsearchUsername, springElasticsearchPassword);
    }
    return builder.build();
  }

  private SSLContext getSSLContext() {
    try (FileInputStream fis = new FileInputStream("./certs/ca/ca.crt")) {
      return TransportUtils.sslContextFromHttpCaCrt(fis);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
