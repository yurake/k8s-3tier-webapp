var users = [
  {
    user: 'webapp',
    pwd: 'webapp',
    roles: [
      {
        role: 'readWrite',
        db: 'webapp'
      }
    ]
  },
  {
    user: "root",
    pwd: "supersecret",
    roles: [
      { role: "clusterMonitor", db: "admin" },
      { role: "read", db: "local" }
    ]
  }
];

for (var i = 0, length = users.length; i < length; ++i) {
  db.createUser(users[i]);
}
