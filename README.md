# PCAP Analyzer

PCAP Analyzer is the culmination of the 12 week [STEP internship program](https://buildyourfuture.withgoogle.com/programs/step/) at Google.


## Table of Contents

* [About the Project](#about-the-project)
  * [Background](#background)
  * [Team](#team)
  * [Built With](#built-with)
* [Setup](#setup)
  * [Adding PCAP files](#adding-pcap-files)
* [Benchmarks](#benchmarks)
* [Contributing](#contributing)
* [Demo](#demo)
* [License](https://github.com/mnuzen/step-capstone-2020/blob/mvp-2/LICENSE)

## About The Project
### Objective
While most people use the internet in some kind of way, not everyone understands the basic fundamentals of how information can be transferred from one machine to another. The goal of this project is to provide causal users with a simple and intuitive way of visualising network communications. By explaining how computers communicate through packets and networks, we hope to educate users on the structure of the internet. 

### Background
While advanced solutions exist for high-level packet analysis, no current service focuses on casual users. Even to computer science students, the internet contains many interconnected components and protocols which can make it difficult to understand. Since 65% of people are visual learners, we hope to provide infographics and helpful depictions of the users’ input data. 

### Overview
Our goal is to help casual users understand and visualize where their computer packets are going and where they are coming from. We hope to have our website be both informative and interactive. 

On the technical front, this means that to build out the PCAP analysis feature of our website, we will need to be able to do the following with our users’ files:

1. File Upload / Data Collection ― since our main source of data are PCAP files, we can either enable PCAP file uploads or retrieve a PCAP file from a user-inputted URL.

2. File Parsing / Data Processing ― we want to be able to extract key attributes from uploaded packets, including packet source, destination, protocol, and time. 

3. File Storage / Data Retrieval ― in order to retrieve the above attributes, we need a way to store our packet information with easy access.

4. File Analysis / Visualizations ― to present our users with their packet information and activity, we will put together a series of intuitive visualizations. These include:
   * Geographical Map ― showing the country or region of packet source/destination
   * Frequent Connections ― enumerating the most common connections made
   * Website Security ― comparing protocols of websites visited by user


### Team
Authors: handeland@google.com, jevingu@google.com, mnuzen@google.com 
Reviewers: arunkaly@, promanov@

### Built With
- [Maven](https://maven.apache.org)
- [Java](https://java.com/en/download/faq/java8.xml)
- [App Engine](https://cloud.google.com/appengine/docs/standard/java)

## Setup: 
1. git clone git@github.com:mnuzen/step-capstone-2020.git
2. Download `GeoLite2-Country.mmdb` from [Maxmind](https://dev.maxmind.com/geoip/geoip2/geolite2/) and place in repo under `resources/`.
3. Add any PCAP files inside of the `resources/files` folder and update `pcap-uploader.html` to include the file path.
4. Edit `resources/keystore.json` to include API keys for Google Maps and [Auth-0 Signal](https://auth0.com/signals/ip)
5. Setup your [GAE credentials](https://cloud.google.com/docs/authentication/production)
6. Run maven project using: `mvn package appengine:run`

### Adding PCAP files: 

1. Place any files inside of the  `resources/files` folder
2. Update `pcap-uploader.html` to include the path to any added PCAP files

## Contributing: 
Before opening a PR, make sure to consult with us through email, or on Github. We have starter issues tagged
with `good first issue`.

## Benchmarks

### MaxmindDB perf: 

On random dataset:
- Averaged 14572 ms for 1000000 requests, 68623.19 rps on i7-9750H single core.
- Cached averaged 17256 ms for 1000000 requests, 57951.98 rps on i7-9750H single core

On uneven dataset (10K uniques) : 
- Averaged 14262 ms for 1000000 requests, 70114.75 rps, 68623.19 rps on i7-9750H single core.
- Cached 100% unique: averaged 13403 ms for 1000000 requests, 74612.02 rps on i7-9750H single core.

Uneven dataset (1k uniques): 
- Cached averaged 12312 ms for 1000000 requests, 81221.57 rps

Uneven dataset (100 uniques):
- Cached averaged 447 ms for 1000000 requests, 2238805.97 rps

On GCP Shell: 
- averaged 121 ms for 1000 requests, 8241.76 rps
- averaged cached 222 ms w/ datashell for 1000 requests, 4504.50 rps

### ReverseDNS

on 150mbps internet / i7-9750h single thread
- averaged 46018 ms for 222 requests, 4.82 rps
- averaged multithreaded 8572 ms for 222 requests, 25.90 rps (about 30 is the max because of rate-limits)

 ### Malicious IP: 
 [Auth0 Signal API](https://auth0.com/signals/docs/)
  - 40,000 requests per day
  - 10 requests per second
  - Cache DB used to mitigate limitations
  
  Benchmark Cache with 210 Unique IP's
  - Non-Cached
       - Avg. lookup time: 73ms
       - Total lookup time: 15,508ms
   - Cached
       - Avg. lookup time: 2ms
       - Total lookup time: 562ms
   - Cache reduces lookup time by ~95%

### Datastore
Datastore retrieval Times:
| Packets | Time in s |
|---------|-----------|
| 15,000  | ~0.5      |
| 30,000  | ~0.9      |
| 60,000  | ~1.8      |
| 120,000 | ~4.2      |

The Entity Properties for the two data objects stored:

1. File Attributes

    | Key | ID | File_Name | PCAP_Entity | My_IP | Upload_Date |
    |-----|----|-----------|-------------|-------|------------|
    |     |    | String    | String      | String| Date       |

2. PCAP File

    | Key | ID | Sources | Desination | Protocol | Size |
    |-----|----|---------|------------|----------|------|
    |     |    | String  | String     | String   | Long |


## Demo
[View on Youtube](https://youtu.be/0yPIX50UWB8)
![4x-compressed](https://user-images.githubusercontent.com/16601367/92042550-eb461400-ed26-11ea-8b7a-c6741a70ad11.gif)

