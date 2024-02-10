const endpoint = "http://localhost:8080/"

async function put(url, data) { 
	const response = await fetch(url, { 
    	method: 'PUT', 
		headers: { 
			'Content-type': 'application/json'
		},
		body: JSON.stringify(data)
	});
	window.location.reload();
	return response; 
} 	

function addFriend(userId) {
	const path = "api/user/profile/add-friend/";
	put(endpoint + path + userId);
}

function removeFriend(userId) {
	const path = "api/user/profile/remove-friend/";
	put(endpoint + path + userId);
}

function followDev(devId) {
	const path = "api/user/profile/follow-dev/";
	put(endpoint + path + devId);
}

function unfollowDev(devId) {
	const path = "api/user/profile/unfollow-dev/";
	put(endpoint + path + devId);
}

function followGame(gameId) {
	const path = "api/game/follow-game/";
	put(endpoint + path + gameId);
}

function unfollowGame(gameId) {
	const path = "api/game/unfollow-game/";
	put(endpoint + path + gameId);
}