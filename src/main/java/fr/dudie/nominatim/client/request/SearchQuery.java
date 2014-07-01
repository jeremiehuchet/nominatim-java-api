package fr.dudie.nominatim.client.request;

/*
 * [license]
 * Nominatim Java API client
 * ~~~~
 * Copyright (C) 2010 - 2014 Dudie
 * ~~~~
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * [/license]
 */

/**
 * Holds keywords for a search request.
 * <p>
 * There is two ways to enter the search query:
 * <ul>
 * <li>using the <i>query<i> <b>q</b> parameter, see {@link SimpleSearchQuery} ;</li>
 * <li>using one or more parameters from the following list : <i>street</i>,
 * <i>city</i>,<i>county</i>,<i>state</i>,<i>country</i>,<i>postal_code</i>, note that this option is currently labeled
 * as <b>experimental</b>, see {@link ExtendedSearchQuery}.</li>
 * </ul>
 * 
 * @author Jeremie Huchet
 * @since 3.0
 */
abstract class SearchQuery extends NominatimRequest {

}
