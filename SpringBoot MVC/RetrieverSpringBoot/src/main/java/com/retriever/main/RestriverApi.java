package com.retriever.main;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.retriever.models.Documents;
import com.retriever.models.JsonBodyObject;

@RestController
public class RestriverApi {

	@Autowired
	private RestTemplate restTemplate;
	private final String url = "https://www.retriever-info.com/doccyexample/documents.json";
	

	@GetMapping("/")
	public String getHomePage() {
		return "<html><body>Main page </body> </html>";
	}
	@GetMapping("/searchWord")
	public String filterSearchWord(@RequestParam(value ="searchWord", defaultValue = "magicthegathering") String searchWord){
		return filterWord(searchWord);
	}
	protected String filterWord(String word) {
		JsonBodyObject response = restTemplate.getForObject(url,JsonBodyObject.class);
		List<Documents> documents = response.getDocuments().stream()
									.filter(document ->document.getStory() != null && document.getStory().contains(word))
									.collect(Collectors.toList());
		StringBuilder builder = new StringBuilder();
		builder.append("<html> <body>");
		documents.forEach(temp -> {
			builder.append("<b>" + temp.getStory() +"</b>");
			builder.append('\n');
		});
		builder.append("<b>" + word + "</b>");
		builder.append("</body></html>");
		return builder.toString();
		
	}
}
