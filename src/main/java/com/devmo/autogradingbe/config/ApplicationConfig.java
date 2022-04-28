package com.devmo.autogradingbe.config;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${git-username}")
    private String gitUserName;

    @Value("${git-access-token}")
    private String gitAccessToken;

    @Value("${testing-github-repository-url}")
    private String testingGithubRepositoryUrl;

    @Bean
    public GitConfig gitConfig() {
        return new GitConfig(
                gitUserName,
                gitAccessToken,
                testingGithubRepositoryUrl,
                new UsernamePasswordCredentialsProvider(gitUserName, gitAccessToken)
        );
    }

}
