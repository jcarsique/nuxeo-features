/*
 * (C) Copyright 2010 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * Contributors:
 * Thomas Roger
 */

package org.nuxeo.opensocial.container.client;

/**
 * @author <a href="mailto:troger@nuxeo.com">Thomas Roger</a>
 */
public class ContainerConfiguration {

    private ContainerConfiguration() {
    }

    public static native String getRepositoryName() /*-{
        return $wnd.nuxeo.container.repositoryName;
    }-*/;

    public static native String getSpaceId() /*-{
        return $wnd.nuxeo.container.id;
    }-*/;

    public static native String getSpaceProviderName() /*-{
        return $wnd.nuxeo.container.spaceProviderName;
    }-*/;

    public static native String getDocumentContextId() /*-{
        return $wnd.nuxeo.container.documentContextId;
    }-*/;

    public static native String getSpaceName() /*-{
        return $wnd.nuxeo.container.spaceName;
    }-*/;

    public static native boolean showPreferencesAfterAddingGadget() /*-{
        return $wnd.nuxeo.container.parameters.showPreferencesAfterAddingGadget;
    }-*/;

    public static native String getUserLanguage() /*-{
        return $wnd.nuxeo.container.parameters.userLanguage;
    }-*/;

}