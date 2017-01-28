# MainActivity - Template generated (Blank activity)
MainActivity
		None

		onCreate()
		onFragmentInteraction()

# MovieFragment - Template generated (Blank Fragment)
MovieFragment
		mMovieAdapter
		mGridViewMovies
		mMovieInformation
		mScreenDensity
		mSortOrder

		MovieFragment()
		onCreate()
		onCreateView()
		OnFragmentInteractionListener()
		getOnlineStatus()
		getScreenDensity()
		setSortMovieAPI()
		onRequestMovieAPI()
		
# Movie - utility function to store film information
Movie
		mTitle
		mYear
		mTrailerPrimaryUri
		mThumbnail

		describeContents()
		writeToParcel()
		Parcelable.Creator()

# MovieAdapter - utility function to add information into the ImageView
MovieAdapter
		mActivity
		mMovies

		MovieAdapter()
		ViewHolder()
		getCount()
		getItem()
		getItemId()
		getView()

# Volley Network
Volley
		Variables

		Methods()


# Picasso Image
Picasso
		Variables

		Methods()

MainActivity -> MovieFragment
MovieFragment <-<> Movies
MovieFragment -> MovieAdapter
MovieAdapter -> Picasso
MovieFragment -> Volley

