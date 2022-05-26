use postgres::{Client, Error, NoTls};

fn main() -> Result<(), Error> {
    let mut client = Client::connect(
        "postgresql://dboperator:operatorpass123@localhost:5243/postgres",
        NoTls,
    )?;

    client.batch_execute(
        "
        CREATE TABLE IF NOT EXISTS app_user (
            id              SERIAL PRIMARY KEY,
            username        VARCHAR UNIQUE NOT NULL,
            password        VARCHAR NOT NULL,
            email           VARCHAR UNIQUE NOT NULL
            )
    ",
    )?;

    for i in 0..1000{
        client.execute(
            "INSERT INTO app_user (username, password, email) VALUES ($1, $2, $3)",
            &[&format!("user{i}"), &format!("mypass"), &format!("user@test.com")],
        )?;
    }
    
    for row in client.query("SELECT id", &[])? {
        let id: i32 = row.get(0);
        client.execute("DELETE FROM app_user WHERE id=$1", &[&id])?;
    }

    Ok(())
}