package org.hisp.dhis.validation.notification;

/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.Sets;
import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.common.DeliveryChannel;
import org.hisp.dhis.common.DxfNamespaces;
import org.hisp.dhis.common.IdentifiableObject;
import org.hisp.dhis.common.MergeMode;
import org.hisp.dhis.notification.NotificationTemplate;
import org.hisp.dhis.schema.annotation.Property;
import org.hisp.dhis.schema.annotation.PropertyRange;
import org.hisp.dhis.user.UserGroup;
import org.hisp.dhis.validation.ValidationRule;

import java.util.Set;

/**
 * @author Halvdan Hoem Grelland
 */
public class ValidationNotificationTemplate
    extends BaseIdentifiableObject
    implements NotificationTemplate
{
    // -------------------------------------------------------------------------
    // Properties
    // -------------------------------------------------------------------------

    private String subjectTemplate;

    private String messageTemplate;

    private Set<ValidationRule> validationRules = Sets.newHashSet();

    private Set<DeliveryChannel> deliveryChannels = Sets.newHashSet();

    private ValidationNotificationRecipient notificationRecipient;

    // -------------------------------------------------------------------------
    // Conditionally relevant properties
    // -------------------------------------------------------------------------

    /**
     * Limit notifications to only users which are in the direct hierarchy of the
     * orgunit. This includes any orgunit in the direct path from the root to the
     * orgunit, as well all children (recursive).
     *
     * Should act like a filter on the configured user groups.
     * In the case of non User recipients, this is not applicable.
     */
    private Boolean notifyUsersInHierarchyOnly;

    private Set<UserGroup> recipientUserGroups = Sets.newHashSet();

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public ValidationNotificationTemplate()
    {
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    @Override
    public String getSubjectTemplate()
    {
        return subjectTemplate;
    }

    public void setSubjectTemplate( String subjectTemplate )
    {
        this.subjectTemplate = subjectTemplate;
    }

    @PropertyRange( min = 1, max = 10000 )
    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    @Override
    public String getMessageTemplate()
    {
        return messageTemplate;
    }

    @PropertyRange( min = 1 )
    @Override
    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public Set<DeliveryChannel> getDeliveryChannels()
    {
        return deliveryChannels;
    }

    public void setDeliveryChannels( Set<DeliveryChannel> deliveryChannels )
    {
        this.deliveryChannels = deliveryChannels;
    }

    public void setMessageTemplate( String messageTemplate )
    {
        this.messageTemplate = messageTemplate;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public Set<ValidationRule> getValidationRules()
    {
        return validationRules;
    }

    public void setValidationRules( Set<ValidationRule> validationRules )
    {
        this.validationRules = validationRules;
    }

    @Property( required = Property.Value.TRUE )
    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public ValidationNotificationRecipient getNotificationRecipient()
    {
        return notificationRecipient;
    }

    public void setNotificationRecipient( ValidationNotificationRecipient notificationRecipient )
    {
        this.notificationRecipient = notificationRecipient;
    }

    @Property( required = Property.Value.FALSE )
    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public Boolean getNotifyUsersInHierarchyOnly()
    {
        return notifyUsersInHierarchyOnly;
    }

    public void setNotifyUsersInHierarchyOnly( Boolean notifyUsersInHierarchyOnly )
    {
        this.notifyUsersInHierarchyOnly = notifyUsersInHierarchyOnly;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public Set<UserGroup> getRecipientUserGroups()
    {
        return recipientUserGroups;
    }

    public void setRecipientUserGroups( Set<UserGroup> recipientUserGroups )
    {
        this.recipientUserGroups = recipientUserGroups;
    }

    // -------------------------------------------------------------------------
    // IdentifiableObject overrides
    // -------------------------------------------------------------------------

    @Override
    public void mergeWith( IdentifiableObject other, MergeMode mergeMode )
    {
        super.mergeWith( other, mergeMode );

        if ( other.getClass().isInstance( this ) )
        {
            ValidationNotificationTemplate that = (ValidationNotificationTemplate) other;

            if ( mergeMode.isReplace() )
            {
                subjectTemplate = that.getSubjectTemplate();
                messageTemplate = that.getMessageTemplate();
                notificationRecipient = that.getNotificationRecipient();
                notifyUsersInHierarchyOnly = that.notifyUsersInHierarchyOnly;
            }
            else if ( mergeMode.isMerge() )
            {
                subjectTemplate = that.getSubjectTemplate() == null ? subjectTemplate : that.getSubjectTemplate();
                messageTemplate = that.getMessageTemplate() == null ? messageTemplate : that.getMessageTemplate();
                notificationRecipient = that.getNotificationRecipient() == null ? notificationRecipient : that.getNotificationRecipient();
                notifyUsersInHierarchyOnly = that.getNotifyUsersInHierarchyOnly() == null ? notifyUsersInHierarchyOnly : that.getNotifyUsersInHierarchyOnly();
            }

            deliveryChannels.clear();
            deliveryChannels.addAll( that.getDeliveryChannels() );

            validationRules.clear();
            validationRules.addAll( that.getValidationRules() );

            recipientUserGroups.clear();
            recipientUserGroups.addAll( that.getRecipientUserGroups() );
        }
    }
}
