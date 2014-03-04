package com.codahale.metrics.jenkins.diskusage;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jenkins.MetricProvider;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.plugins.disk_usage.DiskUsagePlugin;
import jenkins.model.Jenkins;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * @author Stephen Connolly
 */
@Extension(optional = true)
public class DiskUsageMetricProviderImpl extends MetricProvider {
    @NonNull
    @Override
    public MetricSet getMetricSet() {
        final DiskUsagePlugin plugin = Jenkins.getInstance().getPlugin(DiskUsagePlugin.class);
        return metrics(
                metric(name("jenkins", "job", "disk-usage", "total"), new Gauge<Long>() {
                    public Long getValue() {
                        return plugin.getCashedGlobalJobsDiskUsage();
                    }
                }),
                metric(name("jenkins", "job", "disk-usage", "without-builds"), new Gauge<Long>() {
                    public Long getValue() {
                        return plugin.getCashedGlobalJobsWithoutBuildsDiskUsage();
                    }
                }),
                metric(name("jenkins", "job", "disk-usage", "workspaces"), new Gauge<Long>() {
                    public Long getValue() {
                        return plugin.getCashedGlobalWorkspacesDiskUsage();
                    }
                }),
                metric(name("jenkins", "job", "disk-usage", "builds", "total"), new Gauge<Long>() {
                    public Long getValue() {
                        return plugin.getCashedGlobalBuildsDiskUsage();
                    }
                }),
                metric(name("jenkins", "job", "disk-usage", "builds", "locked"), new Gauge<Long>() {
                    public Long getValue() {
                        return plugin.getCashedGlobalLockedBuildsDiskUsage();
                    }
                })
        );
    }
}
