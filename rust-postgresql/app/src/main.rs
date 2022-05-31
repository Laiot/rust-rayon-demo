use postgres::{Client, Error, NoTls};

fn main() -> Result<(), Error> {
    use std::time::Instant;
    let now = Instant::now();

    let mut client = Client::connect(
        "postgresql://operator:operatorpass123@localhost:5432/rust-rayon-demo",
        NoTls,
    )?;

    client.batch_execute(
        "
        CREATE TABLE IF NOT EXISTS app_user (
            id              SERIAL PRIMARY KEY,
            username        VARCHAR UNIQUE NOT NULL,
            password        VARCHAR NOT NULL,
            email           VARCHAR NOT NULL
            )
    ",
    )?;

    for i in 0..1000{
        client.execute(
            "INSERT INTO app_user (username, password, email) VALUES ($1, $2, $3)",
            &[&format!("user{i}"), &format!("mypass"), &format!("user@test.com")],
        )?;
    }
    
    for row in client.query("SELECT id FROM app_user", &[])? {
        let id: i32 = row.get(0);
        client.execute("DELETE FROM app_user WHERE id=$1", &[&id])?;
    }

    let elapsed = now.elapsed();
    println!("Execution ended in {:.2?}", elapsed);

    Ok(())
}