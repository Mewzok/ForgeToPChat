A chat plugin based on the chat system from the MMORPG Tales of Pirates

ToPChat uses four different channels that every message will be sent through: Local, Trade, World, and System.
All four channels have an input and an output that can be changed independently.

Input:
			Every channel's input can individually be turned on and off by the player through a command. When a channel's input is off, you will not personally receive 
		messages from that channel. When the channel's input is on, you will receive every message sent to the channel, assuming you meet the channel's specific extra 
		criteria.

Output:
			Only one channel can be set as your output at a time, meaning you may not speak through multiple channels at once. You must set your output channel through
		a command. Then, whenever you type and send a message, the message will only be sent through the channel you currently have your output set to. It is possible 
		to send a message to a channel that you have turned off the input for, resulting in sending the message but not seeing it yourself.
	
Channels:
		Local:
				The local channel is the default channel meant to be used most often. By default, local chat has no cost or cooldown, however it only reaches
			players within 100 blocks in every direction of you. This is meant to simulate the idea of talking only to those nearby, and while 100 blocks by default is
			a large distance, within Minecraft it feels reasonable. Regardless, everything is available to be changed in the config.
			
		Trade:
				The trade channel is meant to be used specifically to announce your desire to buy, sell, or trade something. To help encourage this, this channel by
			default has a 2 minute cooldown to prevent spam, but costs nothing to use. This allows for money-related interactions without having to spend some every
			time you send your message.
			
		World: 
				The world channel is meant for, as its name implies, sending a message to the entire world. Naturally it's multi-purposed. An example of its potential
				usage would be to ask everyone on the server a question or to converse with a group of players not nearby. By default, world chat has no cooldown but
				costs $100 per message sent. This is to discourage using world chat to buy, sell, or trade, while also implementing a natural way to avoid spam. While
				local chat's existence and world chat's cost may seem unnecessary, the point is to encourage players to gather together to have conversations.
				
		System:
				The system channel is meant exclusively for system events and certain messages from admins. You cannot block this channel's input, nor can a non-admin
			player use this channel for output. This channel will only be used when something system-related occurs, or an event or otherwise important occurrence needs
			to be broadcasted and seen by all players.

Permissions:
	topchat.command.setallchatviewon
	topchat.command.setallchatviewoff
	topchat.command.setworldchatviewon
	topchat.command.setworldchatviewoff
	topchat.command.settradechatviewon
	topchat.command.settradechatviewoff
	topchat.command.setlocalchatviewon
	topchat.command.setlocalchatviewoff
	topchat.command.viewchatstats
	topchat.command.setmodeworld
	topchat.command.setmodetrade
	topchat.command.setmodelocal
	topchat.command.setmodesystem
	topchat.command.chathelp
	topchat.command.chatinfolocal
	topchat.command.chatinfotrade
	topchat.command.chatinfoworld
	topchat.command.chatinfosystem
	topchat.command.chatinfo
	topchat.command.setmode
	topchat.command.topchatreload
	topchat.command.chatsettings