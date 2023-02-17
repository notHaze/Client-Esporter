package com.esporter.both.socket;

import java.io.Serializable;

public enum CommandName implements Serializable{

	SCORE, ADD_TOURNAMENT,MODIFY_TOURNAMENT,DELETE_TOURNAMENT , ADD_TEAM, MODIFY_TEAM, REGISTER_TOURNAMENT, UNREGISTER_TOURNAMENT, CALENDAR, STABLE, LOGIN, LOGOUT, INIT;
}