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
];

for (var i = 0, length = users.length; i < length; ++i) {
  db.createUser(users[i]);
}

db.getSiblingDB("admin").createUser({
  user: "exporter",
  pwd: "supersecret",
  roles: [
    { role: "clusterMonitor", db: "admin" },
    { role: "read", db: "local" }
  ]
})
