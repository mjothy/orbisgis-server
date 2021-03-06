/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.server.wms;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class will be used to feed the GetCapabilities answer with the appropriate
 * values. It's basically a bean storage backed by a HashMap.
 * @author alexis
 */
public class WMSProperties {
    public static final String TITLE = "wms.service.title";
    public static final String RESOURCE_URL = "wms.service.resource.url";
    public static final String RESOURCE_NAME = "wms.service.resource.name";
    public static final String ABSTRACT = "wms.service.abstract";
    public static final String KEYWORDS = "wms.service.keywords";
    public static final String CAP_GET = "wms.capabilities.capabilities.get";
    public static final String CAP_POST = "wms.capabilities.capabilities.post";
    public static final String MAP_GET = "wms.capabilities.map.get";
    public static final String MAP_POST = "wms.capabilities.map.post";
    public static final String FEATURE_GET = "wms.capabilities.featureInfo.get";
    public static final String FEATURE_POST = "wms.capabilities.featureInfo.post";
    public static final String DEBUG_LEVEL = "wms.debug";

    private HashMap<String,Object> values = new HashMap<String, Object>();

    /**
     * Gets all the known keys in a HashSet
     * @return A HashSet with all the known keys.
     */
    public static HashSet<String> getDefaultKeys(){
        HashSet<String> ret = new HashSet<String>();
        ret.add(TITLE);
        ret.add(RESOURCE_URL);
        ret.add(RESOURCE_NAME);
        ret.add(ABSTRACT);
        ret.add(KEYWORDS);
        ret.add(CAP_POST);
        ret.add(CAP_GET);
        ret.add(MAP_POST);
        ret.add(MAP_GET);
        ret.add(FEATURE_POST);
        ret.add(FEATURE_GET);
        ret.add(DEBUG_LEVEL);
        return ret;
    }

    /**
     * Puts the property with the given name with the given value.
     * @param name The property's name
     * @param value The property's value
     */
    public void putProperty(String name, Object value){
        values.put(name, value);
    }

    /**
     * Removes the property with the given name.
     * @param name The name of the property
     * @return The former value.
     */
    public Object removeProperty(String name){
        return values.remove(name);
    }

    /**
     * Gets the object associated to the given property.
     * @param name The name of the property.
     * @return The associated value or null if there is not one.
     */
    public Object getProperty(String name){
        return values.get(name);
    }


}
