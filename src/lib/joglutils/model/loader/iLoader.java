/*
 * iLoader.java
 *
 * Created on February 27, 2008, 10:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package lib.joglutils.model.loader;

import lib.joglutils.model.ModelLoadException;
import lib.joglutils.model.geometry.Model;

/**
 *
 * @author RodgersGB
 */
public interface iLoader {
    public Model load(String path) throws ModelLoadException;
}
