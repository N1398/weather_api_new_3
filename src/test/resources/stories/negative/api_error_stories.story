Meta:
@story API error negative tests

Scenario: Missing query parameter
Given a negative weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return error with status 400, code 1003 and message 'Parameter 'q' not provided.'
When I make an invalid request without query parameter
Then the error response should have status 400, code 1003 and message 'Parameter 'q' not provided.'

Scenario: Invalid API key
Given a negative weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'invalid-key'
Given WireMock is configured to return error with status 401, code 1002 and message 'API key is invalid or not provided.'
When I make an invalid request without query parameter
Then the error response should have status 401, code 1002 and message 'API key is invalid or not provided.'

Scenario: API key disabled
Given a negative weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'disabled-key'
Given WireMock is configured to return error with status 403, code 2006 and message 'API key has been disabled.'
When I make an invalid request without query parameter
Then the error response should have status 403, code 2006 and message 'API key has been disabled.'

Scenario: API key rate limit exceeded
Given a negative weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'rate-limited-key'
Given WireMock is configured to return error with status 400, code 9999 and message 'Internal application error.'
When I make an invalid request without query parameter
Then the error response should have status 400, code 9999 and message 'Internal application error.'