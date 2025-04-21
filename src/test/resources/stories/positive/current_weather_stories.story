Meta:
@story Current weather positive tests

Scenario: Get current weather for Osaka
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Osaka' from file 'osaka_response.json'
When I request current weather for 'Osaka'
Then the response status code should be 200
And the weather data should match expected values:
| key                 | value           |
| location.name       | Osaka           |
| location.region     | Osaka           |
| location.country    | Japan           |
| current.temp_c      | 22.0            |
| current.condition.text | Partly cloudy |

Scenario: Get current weather for Toronto
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Toronto' from file 'toronto_response.json'
When I request current weather for 'Toronto'
Then the response status code should be 200
And the weather data should match expected values:
| key                 | value           |
| location.name       | Toronto         |
| location.region     | Ontario         |
| location.country    | Canada          |
| current.temp_c      | 18.5            |
| current.condition.text | Sunny         |

Scenario: Get current weather for Djougou
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Djougou' from file 'djougou_response.json'
When I request current weather for 'Djougou'
Then the response status code should be 200
And the weather data should match expected values:
| key                 | value           |
| location.name       | Djougou         |
| location.region     | Donga           |
| location.country    | Benin           |
| current.temp_c      | 25.9            |
| current.condition.text | Clear         |

Scenario: Get current weather for Hanoi
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Hanoi' from file 'hanoi_response.json'
When I request current weather for 'Hanoi'
Then the response status code should be 200
And the weather data should match expected values:
| key                 | value           |
| location.name       | Hanoi           |
| location.region     | Ha Noi          |
| location.country    | Vietnam         |
| current.temp_c      | 24.4            |
| current.condition.text | Mist          |

Scenario: Get current weather for Monaco
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Monaco' from file 'monaco_response.json'
When I request current weather for 'Monaco'
Then the response status code should be 200
And the weather data should match expected values:
| key                 | value           |
| location.name       | Monaco-Ville    |
| location.region     |                 |
| location.country    | Monaco          |
| current.temp_c      | 14.3            |
| current.condition.text | Partly cloudy |

Scenario: Get current weather for multiple cities using bulk POST request
Given bulk-request a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
And WireMock is configured to return bulk weather response for request from file 'bulk-request.json' with response from file 'bulk-response.json'
When I send a bulk weather POST request with body from file 'bulk-request.json'
Then the response should contain 4 weather entries
And weather entry 0 should match expected values:
| key                       | value         |
| query.q                  | Hainan        |
| query.location.name      | Hainan        |
| query.location.country   | China         |
| query.current.temp_c     | 20.4          |
| query.current.condition.text | Fog      |
And weather entry 1 should match expected values:
| key                       | value         |
| query.q                  | Oslo          |
| query.location.name      | Oslo          |
| query.location.country   | Norway        |
| query.current.temp_c     | 8.1           |
| query.current.condition.text | Partly cloudy |


Scenario: Get current weather for multiple cities using bulk POST request
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
And WireMock is configured to return bulk weather response for request from file 'bulk-request.json' with response from file 'bulk-response.json'
When I send a bulk weather POST request with body from file 'bulk-request.json'
Then the response status code should be 200
And the response should contain 4 weather entries
And weather entry 0 should match expected values:
| key                       | value         |
| query.q                  | Hainan        |
| query.custom_id          | any-internal-id |
| query.location.name      | Hainan        |
| query.location.region    | Hainan        |
| query.location.country   | China         |
| query.location.lat       | 18.9394       |
| query.location.lon       | 109.4842      |
| query.current.temp_c     | 20.4          |
| query.current.condition.text | Fog      |
And weather entry 1 should match expected values:
| key                       | value         |
| query.q                  | Oslo          |
| query.custom_id          | any-internal-id |
| query.location.name      | Oslo          |
| query.location.region    | Oslo          |
| query.location.country   | Norway        |
| query.current.temp_c     | 8.1           |
| query.current.condition.text | Partly cloudy |
And weather entry 2 should match expected values:
| key                       | value         |
| query.q                  | Hanoi         |
| query.custom_id          | any-internal-id |
| query.location.name      | Hanoi         |
| query.location.country   | Vietnam       |
| query.current.temp_c     | 26.4          |
| query.current.condition.text | Overcast    |
And weather entry 3 should match expected values:
| key                       | value         |
| query.q                  | Toronto       |
| query.custom_id          | any-internal-id |
| query.location.name      | Toronto       |
| query.location.region    | Ontario       |
| query.location.country   | Canada        |
| query.current.temp_c     | 9.3           |
| query.current.condition.text | Sunny      |