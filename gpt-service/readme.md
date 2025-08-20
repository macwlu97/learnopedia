````markdown
# GPT-Service: Local GPT4All Falcon + FastAPI + Spring Boot Client

This microservice allows integration of the **GPT4All Falcon** local model with a Spring Boot application. The FastAPI server exposes a `/generate` endpoint, and the Spring Boot client can send requests and receive responses from the model.

---

## 1️⃣ Python Environment Setup

Install Python 3.10+ if not already installed.

Create a virtual environment:

pwd - /Users/username/gpt4all/gpt4all-project

```bash
python3 -m venv ~/gpt4all-env
source ~/gpt4all-env/bin/activate
````

Install required packages:

```bash
pip install fastapi uvicorn gpt4all
```

---

## 2️⃣ Download GPT4All Falcon Model

1. Download the GPT4All macOS app (.dmg) from [GitHub Releases](https://github.com/nomic-ai/gpt4all/releases).
2. Open the GPT4All GUI and add the Falcon model (e.g., `gpt4all-falcon-newbpe-q4_0`).
3. Locate the downloaded `.gguf` file, e.g.:

```
~/Library/Application Support/nomic.ai/GPT4All/gpt4all-falcon-newbpe-q4_0.gguf
```

---

## 3️⃣ FastAPI Server (Python)

Create a `server.py` file in your project directory:

```python
from fastapi import FastAPI
from pydantic import BaseModel
from gpt4all import GPT4All
import os

# Path to the local model
model_path = os.path.expanduser(
    "~/Library/Application Support/nomic.ai/GPT4All/gpt4all-falcon-newbpe-q4_0.gguf"
)
model = GPT4All(model_path, allow_download=False)

app = FastAPI(title="GPT4All Falcon API")

class Prompt(BaseModel):
    text: str

@app.post("/generate")
def generate(prompt: Prompt):
    response = model.generate(prompt.text)
    return {"response": response}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
```

Run the server:

```bash
source ~/gpt4all-env/bin/activate
python server.py
```

The server runs at:

```
http://localhost:8000/generate
```

Test with `curl`:

```bash
curl -X POST "http://localhost:8000/generate" \
     -H "Content-Type: application/json" \
     -d '{"text": "Hello, GPT4All Falcon!"}'
```

You will receive a JSON response from the model.

---

## 4️⃣ Spring Boot Client

Example Java client using `RestTemplate`:

```java
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class GptClient {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8000/generate";
        String prompt = "Hello from Spring Boot!";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"text\": \"" + prompt + "\"}";

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        System.out.println("GPT4All Response: " + response.getBody().get("response"));
    }
}
```

---

## 5️⃣ Optional Integration with Another Microservice

If you have a microservice, e.g., for Wikipedia integration, you can call GPT4All like this:

```java
String wikiContent = fetchFromWikipedia("Java");
String gptPrompt = "Summarize the article:\n" + wikiContent;
ResponseEntity<Map> response = restTemplate.postForEntity(url, 
        new HttpEntity<>("{\"text\":\"" + gptPrompt + "\"}", headers), Map.class);
System.out.println(response.getBody().get("response"));
```

---

## 6️⃣ Checklist / Summary

* Python 3.10+ and virtualenv ready
* FastAPI + uvicorn + gpt4all installed
* Falcon `.gguf` model in `~/Library/Application Support/nomic.ai/GPT4All/`
* `server.py` ready and running
* Test `curl` works
* Spring Boot client calls the local FastAPI server and receives responses

---

## 💡 Additional Notes

* The `.gguf` file is the new Falcon model format; older `.bin` files also work but `.gguf` is recommended.
* Everything runs locally – no request limits.
* You can modify the `prompt` in the POST JSON or in the Spring Boot client.
* Microservices can be split, e.g., a separate GPT4All server and a Wikipedia service.

---

## 7️⃣ Author / License

Open-source project. Feel free to use and modify as needed.

```
```


## Running the Spring Boot Microservice & Testing with curl

---

### 1️⃣ Run Spring Boot Microservice

If using Maven:

```bash
mvn spring-boot:run

curl -X POST "http://localhost:8080/gpt/generate?prompt=Hello%20from%20Spring%20Boot" \
     -H "Content-Type: application/json"