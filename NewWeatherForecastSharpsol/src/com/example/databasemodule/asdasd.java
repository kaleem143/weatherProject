//package com.example.databasemodule;
//
//import com.example.newweatherforecastsharpsol.R;
//
//import android.database.MatrixCursor;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.BaseColumns;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v4.widget.CursorAdapter;
//import android.support.v4.widget.SimpleCursorAdapter;
//import android.support.v7.widget.SearchView;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//public class CountriesFragment extends Fragment {
//
//	    private boolean mSearchCheck;
//	    private SimpleCursorAdapter mAdapter;
//	    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
//	    public static final String CITY_NAME = "cityName";
//	    private LayoutInflater mInflater;
//	    private ViewGroup mainContainer;
//
//	    private static final String[] COUNTRIES = {
//	            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica",
//	            "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados",
//	            "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil",
//	            "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",
//	            "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros",
//	            "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus",
//	            "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
//	            "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana",
//	            "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada",
//	            "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)",
//	            "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
//	            "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan",
//	            "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg",
//	            "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique",
//	            "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco",
//	            "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
//	            "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
//	            "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia",
//	            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore",
//	            "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka",
//	            "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
//	            "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia",
//	            "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
//	            "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)",
//	            "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
//	    };
//
//	    public static CountriesFragment newInstance(String text) {
//	        CountriesFragment mFragment = new CountriesFragment();
//	        Bundle mBundle = new Bundle();
//	        mBundle.putString(TEXT_FRAGMENT, text);
//	        mFragment.setArguments(mBundle);
//	        return mFragment;
//	    }
//
//	    @Override
//	    public void onCreate(Bundle savedInstanceState) {
//	        super.onCreate(savedInstanceState);
//	        loadHints();
//	    }
//
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//	        View rootView = inflater.inflate(R.layout.fragment_countries, container, false);
//	        mInflater = inflater;
//	        mainContainer = (ViewGroup) rootView.findViewById(R.id.container);
//	        //rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//	        return rootView;
//	    }
//
//	    @Override
//	    public void onActivityCreated(Bundle savedInstanceState) {
//	        super.onActivityCreated(savedInstanceState);
//	        setHasOptionsMenu(true);
//	    }
//
//	    @Override
//	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//	        super.onCreateOptionsMenu(menu, inflater);
//	        inflater.inflate(R.menu.menu, menu);
//
//	        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
//	        searchView.setQueryHint(this.getString(R.string.search));
//
//	        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
//	                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
//
//
//	        searchView.setSuggestionsAdapter(mAdapter);
//	        searchView.setOnQueryTextListener(onQuerySearchView);
//	        searchView.setOnSuggestionListener(onQuerySuggestion);
//
//	        menu.findItem(R.id.menu_add).setVisible(true);
//	        menu.findItem(R.id.menu_search).setVisible(true);
//
//	        mSearchCheck = false;
//	    }
//
//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//
//	        switch (item.getItemId()) {
//
//	            case R.id.menu_add:
//	                addCountry();
//	                break;
//
//	            case R.id.menu_search:
//	                mSearchCheck = true;
//	                break;
//	        }
//	        return true;
//	    }
//
//	    private void addCountry() {
//	        final ViewGroup newView = (ViewGroup) mInflater.inflate(R.layout.country_row, mainContainer, false);
//	        final TextView countryName = (TextView) newView.findViewById(android.R.id.text1);
//	        countryName.setText(COUNTRIES[(int) (Math.random() * COUNTRIES.length)]);
//
//
//	        final Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
//	        final Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
//	        fadeIn.setFillAfter(false);
//	        fadeOut.setFillAfter(false);
//
//	        final ImageButton closeButton = (ImageButton) newView.findViewById(R.id.delete_button);
//	        closeButton.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                newView.startAnimation(fadeOut);
//	                new Handler().postDelayed(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                        mainContainer.removeView(newView);
//	                    }
//	                }, fadeOut.getDuration());
//	            }
//	        });
//
//	        mainContainer.addView(newView, 0);
//	        newView.startAnimation(fadeIn);
//	    }
//
//	    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
//	        @Override
//	        public boolean onQueryTextSubmit(String query) {
//	            mSearchCheck = false;
//	            return false;
//	        }
//
//	        @Override
//	        public boolean onQueryTextChange(String query) {
//	            if (mSearchCheck) {
//	                // implement your search here
//	                giveSuggestions(query);
//	            }
//	            return false;
//	        }
//	    };
//
//	    private void giveSuggestions(String query) {
//	        final MatrixCursor cursor = new MatrixCursor(new String[]{BaseColumns._ID, CITY_NAME});
//	        for (int i = 0; i < COUNTRIES.length; i++) {
//	            if (COUNTRIES[i].toLowerCase().contains(query.toLowerCase()))
//	                cursor.addRow(new Object[]{i, COUNTRIES[i]});
//	        }
//	        mAdapter.changeCursor(cursor);
//	    }
//
//	    private SearchView.OnSuggestionListener onQuerySuggestion = new SearchView.OnSuggestionListener() {
//	        @Override
//	        public boolean onSuggestionSelect(int position) {
//	            return false;
//	        }
//
//	        @Override
//	        public boolean onSuggestionClick(int position) {
//	            return false;
//	        }
//	    };
//
//	    private void loadHints() {
//	        final String[] from = new String[]{CITY_NAME};
//	        final int[] to = new int[]{android.R.id.text1};
//	        mAdapter = new SimpleCursorAdapter(getActivity(),
//	                R.layout.hint_row,
//	                null,
//	                from,
//	                to,
//	                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//	    }
//	}
//
