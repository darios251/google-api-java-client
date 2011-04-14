/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.http;

import java.io.IOException;

/**
 * Thread-safe light-weight HTTP request factory layer on top of the HTTP transport that has an
 * optional {@link HttpRequestHandler HTTP request handler} for initializing requests.
 *
 * <p>
 * For example, to use a particular authorization header across all requests, use:
 * </p>
 *
 * <pre>
  public static HttpRequestFactory createRequestFactory(HttpTransport transport) {
    return transport.createRequestFactory(new HttpRequestHandler() {
      public void handle(HttpRequest request) {
        request.headers.authorization = "...";
      }
    });
  }
 * </pre>
 *
 * @since 1.4
 * @author Yaniv Inbar
 */
public final class HttpRequestFactory {

  /** HTTP transport. */
  public final HttpTransport transport;

  /**
   * HTTP request handler for initializing requests immediately after calling
   * {@link HttpTransport#buildRequest()} or {@code null} for none.
   */
  public final HttpRequestHandler initializer;

  /**
   * @param transport HTTP transport
   * @param initializer HTTP request handler for initializing requests or {@code null} for none
   */
  HttpRequestFactory(HttpTransport transport, HttpRequestHandler initializer) {
    this.transport = transport;
    this.initializer = initializer;
  }

  /**
   * Builds a request for the given HTTP method, URL, and content.
   *
   * @param method HTTP request method
   * @param url HTTP request URL or {@code null} for none
   * @param content HTTP request content or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildRequest(HttpMethod method, GenericUrl url, HttpContent content)
      throws IOException {
    HttpRequest request = transport.buildRequest();
    if (initializer != null) {
      initializer.handle(request);
    }
    request.method = method;
    request.url = url;
    request.content = content;
    return request;
  }

  /**
   * Builds a {@code DELETE} request for the given URL.
   *
   * @param url HTTP request URL or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildDeleteRequest(GenericUrl url) throws IOException {
    return buildRequest(HttpMethod.DELETE, url, null);
  }

  /**
   * Builds a {@code GET} request for the given URL.
   *
   * @param url HTTP request URL or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildGetRequest(GenericUrl url) throws IOException {
    return buildRequest(HttpMethod.GET, url, null);
  }

  /**
   * Builds a {@code POST} request for the given URL and content.
   *
   * @param url HTTP request URL or {@code null} for none
   * @param content HTTP request content or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildPostRequest(GenericUrl url, HttpContent content) throws IOException {
    return buildRequest(HttpMethod.POST, url, content);
  }

  /**
   * Builds a {@code PUT} request for the given URL and content.
   *
   * @param url HTTP request URL or {@code null} for none
   * @param content HTTP request content or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildPutRequest(GenericUrl url, HttpContent content) throws IOException {
    return buildRequest(HttpMethod.PUT, url, content);
  }

  /**
   * Builds a {@code PATCH} request for the given URL and content.
   *
   * @param url HTTP request URL or {@code null} for none
   * @param content HTTP request content or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildPatchRequest(GenericUrl url, HttpContent content) throws IOException {
    return buildRequest(HttpMethod.PATCH, url, content);
  }


  /**
   * Builds a {@code HEAD} request for the given URL.
   *
   * @param url HTTP request URL or {@code null} for none
   * @return new HTTP request
   */
  public HttpRequest buildHeadRequest(GenericUrl url) throws IOException {
    return buildRequest(HttpMethod.HEAD, url, null);
  }
}