package ru.ping.pda.Data_Model

data class Command_info(
    var command_id:String = "",
    var command_pin:String = "",
    var Command_name: String ="",
    var command_owner:String ="",
    var command_fd: ArrayList<String> = ArrayList<String>(),
    var command_message:String = ""
                        )
