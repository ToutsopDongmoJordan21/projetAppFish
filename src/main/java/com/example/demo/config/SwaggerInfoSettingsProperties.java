package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("swagger-info-settings")
public class SwaggerInfoSettingsProperties {
    /**
     * description.
     */
    private String description;
    /**
     * email.
     */
    private String email;
    /**
     * license.
     */
    private String license;
    /**
     * license url.
     */
    private String licenseUrl;
    /**
     * terme of service.
     */
    private String termsOfServiceUrl;
    /**
     * titre.
     */
    private String title;
    /**
     * url.
     */
    private String url;
    /**
     * version.
     */
    private String version;
    /**
     * name.
     */
    private String name;

    /**
     * @return the description
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * @param pdescription
     *            the description to set
     */
    public final void setDescription(final String pdescription) {
        this.description = pdescription;
    }

    /**
     * @return the email
     */
    public final String getEmail() {
        return this.email;
    }

    /**
     * @param pemail
     *            the email to set
     */
    public final void setEmail(final String pemail) {
        this.email = pemail;
    }

    /**
     * @return the license
     */
    public final String getLicense() {
        return this.license;
    }

    /**
     * @param plicense
     *            the license to set
     */
    public final void setLicense(final String plicense) {
        this.license = plicense;
    }

    /**
     * @return the licenseUrl
     */
    public final String getLicenseUrl() {
        return this.licenseUrl;
    }

    /**
     * @param plicenseUrl
     *            the licenseUrl to set
     */
    public final void setLicenseUrl(final String plicenseUrl) {
        this.licenseUrl = plicenseUrl;
    }

    /**
     * @return the termsOfServiceUrl
     */
    public final String getTermsOfServiceUrl() {
        return this.termsOfServiceUrl;
    }

    /**
     * @param pServiceUrl
     *            the termsOfServiceUrl to set
     */
    public final void setTermsOfServiceUrl(final String pServiceUrl) {
        this.termsOfServiceUrl = pServiceUrl;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     * @param ptitle
     *            the title to set
     */
    public final void setTitle(final String ptitle) {
        this.title = ptitle;
    }

    /**
     * @return the url
     */
    public final String getUrl() {
        return this.url;
    }

    /**
     * @param purl
     *            the url to set
     */
    public final void setUrl(final String purl) {
        this.url = purl;
    }

    /**
     * @return the version
     */
    public final String getVersion() {
        return this.version;
    }

    /**
     * @param pversion
     *            the version to set
     */
    public final void setVersion(final String pversion) {
        this.version = pversion;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @param nom
     *            the name to set
     */
    public final void setName(final String nom) {
        this.name = nom;
    }
}

