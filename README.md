# PopularMovies
Popular Movies - Projects 1 & 2 of the Udacity Android Developer Nanodegree

## Getting Started

Popular Movies provides an interface for the TMDB movie API. The information covers stages 1 & 2 of the implementation guide. Supporting both tablet and phones, the application consumes information from the TMDB API and presents this in a simple UX. 

Popular Movies Image

Tablet Version (Nexus 9)

![Popular Movies tablet application](images/nexus9_screenshot_med.png?raw=true "Project 1 & 2")

Phone Version (Nexus 6p)

![Popular Movies phone application](images/nexus6p_screenshot_med.png?raw=true "Project 1 & 2")

## Application architecture (generated using the quickwindiagram tool)

Libraries
  Picasso
  Volley


### Prerequisites

The project requires the Volley library and a valid TMDB API key to compile and run. Load Volley into the project directory. An empty folder marked Volley is the one in which the Volley project should be loaded.

Note: 
Volley has now been moved to the following location: https://github.com/google/volley
A valid TMDB API key can be accessed via the TMDB website.

### How to build

1. git clone https://github.com/rosera/PopularMovies.git
2. cd PopularMovies
3. git clone https://github.com/google/volley.git (i.e. into the existing subdirectory of PopularMovies)
4. Start Android Studio
5. Import the PopularMovies project
6. Create/Amend a new gradle file - gradle.properties
7. Edit gradle.properties and add TMDB_API_KEY="Enter Your valid API KEY" (Note: if you dont already have a valid TMDB API key, sign up at https://developers.themoviedb.org/3/getting-started).

![Popular Movies phone application](images/gradle-properties-screenshot.png?raw=true "Gradle Properties")

8. Compile and run the code

## Acknowledgments

* Massive thanks to the Udacity coaches and fellow students for the help and support

