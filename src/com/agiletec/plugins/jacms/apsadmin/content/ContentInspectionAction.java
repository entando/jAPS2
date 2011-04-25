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
package com.agiletec.plugins.jacms.apsadmin.content;

import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.util.EntityAttributeIterator;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.plugins.jacms.aps.system.services.content.ContentUtilizer;
import com.agiletec.plugins.jacms.aps.system.services.content.model.CmsAttributeReference;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.content.model.ContentRecordVO;
import com.agiletec.plugins.jacms.aps.system.services.content.model.extraAttribute.IReferenceableAttribute;

/**
 * @author E.Santoboni - M.E.Minnai
 */
public class ContentInspectionAction extends AbstractContentAction {
	
	public List<IPage> getReferencingPages() {
		List<IPage> referencingPages;
		try {
			referencingPages = ((ContentUtilizer) this.getPageManager()).getContentUtilizers(this.getContentId());
		} catch (Throwable t) {
			String msg = "Error getting referencing pages by content " + this.getContentId();
			ApsSystemUtils.logThrowable(t, this, "setReferencingPages", msg );
			throw new RuntimeException(msg, t);
		}
		return referencingPages;
	}
	
	public List<ContentRecordVO> getReferencingContents() {
		List<ContentRecordVO> referencingContents = null;
		try {
			List<String> referencingContentsId = ((ContentUtilizer) this.getContentManager()).getContentUtilizers(this.getContentId());
			if (null != referencingContentsId) {
				referencingContents = new ArrayList<ContentRecordVO>();
				for (int i = 0; i < referencingContentsId.size(); i++) {
					ContentRecordVO currentReferencedContent = this.getContentManager().loadContentVO(referencingContentsId.get(i));
					referencingContents.add(currentReferencedContent);
				}
			}
		} catch (Throwable t) {
			String msg = "Error getting referencing contents by content " + this.getContentId();
			ApsSystemUtils.logThrowable(t, this, "getReferencingContents", msg );
			throw new RuntimeException(msg, t);
		}
		return referencingContents;
	}
	
	public List<ContentRecordVO> getReferencedContents() {
		List<ContentRecordVO> referencedContents = new ArrayList<ContentRecordVO>();
		try {
			Content content = this.getContent();
			if (null == content) return referencedContents;
			EntityAttributeIterator attributeIter = new EntityAttributeIterator(content);
			while (attributeIter.hasNext()) {
				AttributeInterface currAttribute = (AttributeInterface) attributeIter.next();
				if (currAttribute instanceof IReferenceableAttribute) {
					IReferenceableAttribute cmsAttribute = (IReferenceableAttribute) currAttribute;
					List<CmsAttributeReference> refs = cmsAttribute.getReferences(this.getLangs());
					for (int scanRefs = 0; scanRefs < refs.size(); scanRefs++) {
						CmsAttributeReference ref = refs.get(scanRefs);
						if (null == ref.getRefContent()) continue; 
						ContentRecordVO currentReferencedContent = 
							this.getContentManager().loadContentVO(ref.getRefContent());
						if (null == currentReferencedContent) continue; 
						if (!referencedContents.contains(currentReferencedContent)) {
							referencedContents.add(currentReferencedContent);
						}
					}
				}
			}
		} catch (Throwable t) {
			String msg = "Error getting referenced contents by content " + this.getContentId();
			ApsSystemUtils.logThrowable(t, this, "getReferencedContents", msg );
			throw new RuntimeException(msg, t);
		}
		return referencedContents;
	}
	
	public List<IPage> getReferencedPages() {
		List<IPage> referencedPages = new ArrayList<IPage>();
		try {
			Content content = this.getContent();
			if (null == content) return referencedPages;
			EntityAttributeIterator attributeIter = new EntityAttributeIterator(content);
			while (attributeIter.hasNext()) {
				AttributeInterface currAttribute = (AttributeInterface) attributeIter.next();
				if (currAttribute instanceof IReferenceableAttribute) {
					IReferenceableAttribute cmsAttribute = (IReferenceableAttribute) currAttribute;
					List<CmsAttributeReference> refs = cmsAttribute.getReferences(this.getLangs());
					for (int scanRefs = 0; scanRefs < refs.size(); scanRefs++) {
						CmsAttributeReference ref = refs.get(scanRefs);
						if (null == ref.getRefPage()) continue;
						IPage page = this.getPageManager().getPage(ref.getRefPage());
						if (null == page) continue; 
						if (!referencedPages.contains(page)) {
							referencedPages.add(page);
						}
					}
				}
			}
		} catch (Throwable t) {
			String msg = "Error getting referenced pages by content " + this.getContentId();
			ApsSystemUtils.logThrowable(t, this, "getReferencedPages", msg );
			throw new RuntimeException(msg, t);
		}
		return referencedPages;
	}
	
	@Override
	public Content getContent() {
		if (this._content != null) return this._content;
		try {
			this._content = this.getContentManager().loadContent(this.getContentId(), this.isCurrentPublicVersion());
		} catch (Throwable t) {
			String msg = "Error getting the content " + this.getContentId();
			ApsSystemUtils.logThrowable(t, this, "getContent", msg);
			throw new RuntimeException(msg, t);
		}
		return _content;
	}
	
	public void setContentId(String contentId) {
		this._contentId = contentId;
	}
	public String getContentId() {
		return _contentId;
	}
	
	public boolean isCurrentPublicVersion() {
		return _currentPublicVersion;
	}
	public void setCurrentPublicVersion(boolean currentPublicVersion) {
		this._currentPublicVersion = currentPublicVersion;
	}
	
	public void setPageManager(IPageManager pageManager) {
		this._pageManager = pageManager;
	}
	public IPageManager getPageManager() {
		return _pageManager;
	}
	
	private String _contentId;
	private boolean _currentPublicVersion;
	private Content _content;
	
	private IPageManager _pageManager;
	
}