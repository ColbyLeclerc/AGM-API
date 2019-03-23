package io.colby.modules.auth.route;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.model.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AuthController {

    //TODO have token generated from program vs passed as string

    @Autowired
    AuthRepository authRepository;

    @RequestMapping(value = {"/auth"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Auth> createAuth(
            @Valid @RequestBody Auth request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        //TODO create admin auth
//        Optional<Auth> authRec = new AuthService().getFromToken(auth);
//
//        if (!authRec.isPresent()){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return CompletableFuture.completedFuture(null);
//        }

        request.setToken(UUID.randomUUID());

        Auth authEnt = authRepository.save(request);
        Optional<Auth> authOpt = authRepository.findAuthByToken(authEnt.getToken().toString());

        if (!authOpt.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(authOpt.get());

    }

    @RequestMapping(value = {"/auth/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Auth> getSingleEnclosure(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        //TODO check this is an admin (maybe add isAdmin column, or a separate perms table)
//        Optional<Auth> authRec = new AuthService().getFromToken(auth);
//
//        if (!authRec.isPresent()){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return CompletableFuture.completedFuture(null);
//        }

        Optional<Auth> authSearch = authRepository.findAuthByAuthId(id);

        if (!authSearch.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(authSearch.get());

    }

}
