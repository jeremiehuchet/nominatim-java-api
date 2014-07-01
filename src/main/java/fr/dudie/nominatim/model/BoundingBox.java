package fr.dudie.nominatim.model;

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
 * Represents a viewport.
 * 
 * <pre>
 *     "boundingbox": [
 *         "48.1190567016602",
 *         "48.1191635131836",
 *         "-1.6499342918396",
 *         "-1.64988231658936"
 *     ],
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public class BoundingBox {

    /** The north bound of the boundingbox. */
    private double north;

    /** The west bound of the boundingbox. */
    private double west;

    /** The east bound of the boundingbox. */
    private double east;

    /** The south bound of the boundingbox. */
    private double south;

    /**
     * Gets the north.
     * 
     * @return the north
     */
    public final double getNorth() {

        return north;
    }

    /**
     * Gets the north.
     * 
     * @return the north
     */
    public final int getNorthE6() {

        return (int) (north * 1E6);
    }

    /**
     * Sets the north.
     * 
     * @param north
     *            the north to set
     */
    public final void setNorth(final double north) {

        this.north = north;
    }

    /**
     * Sets the north.
     * 
     * @param north
     *            the north to set
     */
    public final void setNorthE6(final int north) {

        this.north = north / 1E6;
    }

    /**
     * Gets the west.
     * 
     * @return the west
     */
    public final double getWest() {

        return west;
    }

    /**
     * Gets the west.
     * 
     * @return the west
     */
    public final int getWestE6() {

        return (int) (west * 1E6);
    }

    /**
     * Sets the west.
     * 
     * @param west
     *            the west to set
     */
    public final void setWest(final double west) {

        this.west = west;
    }

    /**
     * Sets the west.
     * 
     * @param west
     *            the west to set
     */
    public final void setWestE6(final int west) {

        this.west = west / 1E6;
    }

    /**
     * Gets the east.
     * 
     * @return the east
     */
    public final double getEast() {

        return east;
    }

    /**
     * Gets the east.
     * 
     * @return the east
     */
    public final int getEastE6() {

        return (int) (east * 1E6);
    }

    /**
     * Sets the east.
     * 
     * @param east
     *            the east to set
     */
    public final void setEast(final double east) {

        this.east = east;
    }

    /**
     * Sets the east.
     * 
     * @param east
     *            the east to set
     */
    public final void setEastE6(final int east) {

        this.east = east / 1E6;
    }

    /**
     * Gets the south.
     * 
     * @return the south
     */
    public final double getSouth() {

        return south;
    }

    /**
     * Gets the south.
     * 
     * @return the south
     */
    public final int getSouthE6() {

        return (int) (south * 1E6);
    }

    /**
     * Sets the south.
     * 
     * @param south
     *            the south to set
     */
    public final void setSouth(final double south) {

        this.south = south;
    }

    /**
     * Sets the south.
     * 
     * @param south
     *            the south to set
     */
    public final void setSouthE6(final int south) {

        this.south = south / 1E6;
    }

}
