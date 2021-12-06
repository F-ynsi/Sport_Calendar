const fetchDataBtn = document.querySelector('#fetchdata')
const result = document.querySelector('#result')
const eventResult = document.querySelector('#eventResult')
const postSport = document.querySelector('#postSport')

async function postData(sport) {
  await fetch('http://localhost:8080/eventsBySport', {
    method: 'POST',
    mode: 'cors',
    cache: 'reload',
    credentials: 'same-origin',
    headers: {'Content-Type': 'application/json'},
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: JSON.stringify(sport) 
  }).then(res => {
        return res.json();
        }
  ).then( events => {
    let eventTable =
    `<tr>
        <th>No</th>
        <th>Starts at</th>
        <th>Sport</th>
        <th>Home Team</th>
        <th>Away Team</th>
    </tr>`
    events.map((event, index) => eventTable +=`
    <tr>
        <td>${index + 1}</td>
        <td>${event.startDateTime}</td>
        <td>${event.sport}</td>
        <td>${event.homeTeamName}</td>
        <td>${event.awayTeamName}</td>
    </tr>
    `)

    return eventResult.innerHTML = events.length > 0 ? eventTable : "No event found!"
  });
}

postSport.addEventListener('change', function() {
  postData(this.value)
});

window.onload = function() {
  postData('all')
}