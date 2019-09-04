ScalaWorld 2019 guardrail workshop
===

For future internet historians, this was a journey through setting up a
brand new project using guardrail from scratch, using akka-http.

Throughout we're adding additional implementation details and seeing what
happens if we don't supply enough arguments.

Interesting note
===

The script I ran to generate this was:

```bash
while true; do
  git ls-files | \
    entr -cd sh -c 'git rm -r --cached . && git add . && git commit -m "updating" && git push origin @:master'
done
```

