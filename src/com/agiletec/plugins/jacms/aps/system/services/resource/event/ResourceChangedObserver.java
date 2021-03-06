/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.plugins.jacms.aps.system.services.resource.event;

import com.agiletec.aps.system.common.notify.ObserverService;

/**
 * Interface of the observer services of resource changing events
 * @author E.Santoboni - M.Diana
 */
public interface ResourceChangedObserver extends ObserverService {
	
	/**
	 * Refresh the service by a changing resource event.
	 * @param event The event
	 */
	public void updateFromResourceChanged(ResourceChangedEvent event);
	
}
