package com.gamingmesh.jobs.commands.list;

import org.bukkit.command.CommandSender;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.commands.Cmd;
import com.gamingmesh.jobs.commands.JobCommand;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;

public class expboost implements Cmd {

    @Override
    @JobCommand(2300)
    public boolean perform(Jobs plugin, CommandSender sender, String[] args) {
	if (args.length > 2 || args.length <= 1) {
	    Jobs.getCommandManager().sendUsage(sender, "expboost");
	    return true;
	}

	double rate = 1.0;
	if (!args[1].equalsIgnoreCase("all") && !args[0].equalsIgnoreCase("reset"))
	    try {
		rate = Double.parseDouble(args[1]);
	    } catch (NumberFormatException e) {
		Jobs.getCommandManager().sendUsage(sender, "expboost");
		return true;
	    }

	Job job = Jobs.getJob(args[0]);
	if (job == null) {
	    sender.sendMessage(Jobs.getLanguage().getMessage("general.error.job"));
	    return true;
	}

	if (args[0].equalsIgnoreCase("reset") && args[1].equalsIgnoreCase("all")) {
	    for (Job one : Jobs.getJobs()) {
		one.addBoost(CurrencyType.EXP, 1.0);
	    }

	    sender.sendMessage(Jobs.getLanguage().getMessage("command.expboost.output.allreset"));
	    return true;
	} else if (args[0].equalsIgnoreCase("reset")) {
	    boolean found = false;
	    for (Job one : Jobs.getJobs()) {
		if (one.getName().equalsIgnoreCase(args[1])) {
		    one.addBoost(CurrencyType.EXP, 1.0);
		    found = true;
		    break;
		}
	    }

	    if (found) {
		sender.sendMessage(Jobs.getLanguage().getMessage("command.expboost.output.jobsboostreset", "%jobname%", job.getName()));
		return true;
	    }
	}

	if (args[0].equalsIgnoreCase("all")) {
	    for (Job one : Jobs.getJobs()) {
		one.addBoost(CurrencyType.EXP, rate);
	    }

	    sender.sendMessage(Jobs.getLanguage().getMessage("command.expboost.output.boostalladded", "%boost%", rate));
	    return true;
	}

	job.addBoost(CurrencyType.EXP, rate);
	sender.sendMessage(Jobs.getLanguage().getMessage("command.expboost.output.boostadded", "%boost%", rate, "%jobname%", job.getName()));
	return true;
    }
}
